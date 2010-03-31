package com.grt192.event.component;

public interface JagEncoderListener {
    public void countDidChange(JagEncoderEvent e);
    public void rotationDidStart(JagEncoderEvent e);
    public void rotationDidStop(JagEncoderEvent e);
    public void changedDirection(JagEncoderEvent e);
}
