package edu.psu.sweng500.bacnetserver.bacnet4j2.npdu;

import edu.psu.sweng500.bacnetserver.bacnet4j2.apdu.APDU;
import edu.psu.sweng500.bacnetserver.bacnet4j2.event.ExceptionDispatch;
import edu.psu.sweng500.bacnetserver.bacnet4j2.exception.BACnetException;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.constructed.Address;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.constructed.ServicesSupported;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.OctetString;
import com.serotonin.util.queue.ByteQueue;

abstract public class IncomingRequestParser {
	private final Network network;
	protected final ByteQueue originalQueue;
	protected final ByteQueue queue;
	protected Address from;
	protected OctetString linkService;
	protected ServicesSupported servicesSupported;

	protected IncomingRequestParser(Network network, ByteQueue queue, OctetString localFrom) {
		this.network = network;
		this.queue = queue;
		this.originalQueue = (ByteQueue) queue.clone();
		linkService = localFrom;
	}

	public void run() {
		try {
			// Determine the supported services. Logical branch supports
			// testing.
			if (network == null || network.getTransport() == null) {
				// Testing
				servicesSupported = new ServicesSupported();
				servicesSupported.setAll(true);
			} else
				servicesSupported = network.getTransport().getLocalDevice().getServicesSupported();

			parseFrame();

			// Create the APDU.
			APDU apdu = parseApdu();
			if (apdu != null && network != null) {
				if (network.isLocal(from))
					linkService = null;
				network.getTransport().incomingApdu(apdu, from, linkService);
			}
		} catch (Exception e) {
			ExceptionDispatch.fireReceivedException(e);
		} catch (Throwable t) {
			ExceptionDispatch.fireReceivedThrowable(t);
		}
	}

	/**
	 * Parse out the network-specific data up until the NPCI.
	 * 
	 * @throws MessageValidationAssertionException
	 */
	abstract protected void parseFrame() throws MessageValidationAssertionException;

	public APDU parseApdu() throws Exception {
		// Network layer protocol control information. See 6.2.2
		NPCI npci = new NPCI(queue);
		if (npci.getVersion() != 1)
			throw new MessageValidationAssertionException("Invalid protocol version: " + npci.getVersion());
		if (npci.isNetworkMessage())
			return null; // throw new
							// MessageValidationAssertionException("Network
							// messages are not supported");

		// Check the destination network number work and do not respond to
		// foreign networks requests
		if (npci.hasDestinationInfo()) {
			int destNet = npci.getDestinationNetwork();
			if (destNet > 0 && destNet != 0xffff && network.getLocalNetworkNumber() > 0
					&& network.getLocalNetworkNumber() != destNet)
				return null;
		}

		if (npci.hasSourceInfo())
			from = new Address(npci.getSourceNetwork(), npci.getSourceAddress());
		else
			from = new Address(linkService);

		// Create the APDU.
		try {
			return APDU.createAPDU(servicesSupported, queue);
		} catch (BACnetException e) {
			// If it's already a BACnetException, don't bother wrapping it.
			throw e;
		} catch (Exception e) {
			throw new BACnetException("Error while creating APDU: ", e);
		}
	}
}
