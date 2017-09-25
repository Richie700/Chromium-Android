
// Copyright 2014 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

// This file is autogenerated by:
//     mojo/public/tools/bindings/mojom_bindings_generator.py
// For:
//     media/mojo/interfaces/video_encode_accelerator.mojom
//

package org.chromium.media.mojom;

import org.chromium.base.annotations.SuppressFBWarnings;
import org.chromium.mojo.bindings.DeserializationException;


public interface VideoEncodeAccelerator extends org.chromium.mojo.bindings.Interface {



    public static final class Error {
    
    
        public static final int ILLEGAL_STATE = 0;
    
        public static final int INVALID_ARGUMENT = ILLEGAL_STATE + 1;
    
        public static final int PLATFORM_FAILURE = INVALID_ARGUMENT + 1;
    
    
        private static final boolean IS_EXTENSIBLE = false;
    
        public static boolean isKnownValue(int value) {
            switch (value) {
                case 0:
                case 1:
                case 2:
                    return true;
            }
            return false;
        }
    
        public static void validate(int value) {
            if (IS_EXTENSIBLE || isKnownValue(value))
                return;
    
            throw new DeserializationException("Invalid enum value.");
        }
    
        private Error() {}
    
    }


    public interface Proxy extends VideoEncodeAccelerator, org.chromium.mojo.bindings.Interface.Proxy {
    }

    Manager<VideoEncodeAccelerator, VideoEncodeAccelerator.Proxy> MANAGER = VideoEncodeAccelerator_Internal.MANAGER;


    void initialize(
int inputFormat, org.chromium.gfx.mojom.Size inputVisibleSize, int outputProfile, int initialBitrate, VideoEncodeAcceleratorClient client, 
InitializeResponse callback);

    interface InitializeResponse extends org.chromium.mojo.bindings.Callbacks.Callback1<Boolean> { }



    void encode(
VideoFrame frame, boolean forceKeyframe, 
EncodeResponse callback);

    interface EncodeResponse extends org.chromium.mojo.bindings.Callbacks.Callback0 { }



    void useOutputBitstreamBuffer(
int bitstreamBufferId, org.chromium.mojo.system.SharedBufferHandle buffer);



    void requestEncodingParametersChange(
int bitrate, int framerate);


}