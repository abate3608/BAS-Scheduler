package edu.psu.sweng500.bacnetserver.bacnet4j2.npdu.mstp;

public interface FrameResponseListener {
    void response(Frame frame);

    void timeout();
}
