/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.spp.ddoots;

import com.google.android.gms.common.ConnectionResult;
import android.content.Context;
import android.content.res.Resources;



/**
 * Map error codes to error messages.
 */
public class LocationServiceErrorMessages {
	 public static final int connected=0x7f050016;
     public static final int connection_error_code=0x7f050000;
     public static final int connection_error_code_unknown=0x7f050017;
     public static final int connection_error_disabled=0x7f050001;
     public static final int connection_error_internal=0x7f050002;
     public static final int connection_error_invalid=0x7f050003;
     public static final int connection_error_invalid_account=0x7f050004;
     public static final int connection_error_license_check_failed=0x7f050005;
     public static final int connection_error_message=0x7f050006;
     public static final int connection_error_misconfigured=0x7f050007;
     public static final int connection_error_missing=0x7f050008;
     public static final int connection_error_needs_resolution=0x7f050009;
     public static final int connection_error_network=0x7f05000a;
     public static final int connection_error_outdated=0x7f05000b;
     public static final int connection_error_sign_in_required=0x7f05000c;
     public static final int connection_error_unknown=0x7f05000d;
     public static final int connection_failed=0x7f050018;
     public static final int disconnected=0x7f050019;
     public static final int get_address=0x7f05001a;
     public static final int get_location=0x7f05001b;
     public static final int illegal_argument_exception=0x7f05001c;
     public static final int invalid_action=0x7f05001d;
     public static final int invalid_integer_id=0x7f05001e;
    // Don't allow instantiation
    private LocationServiceErrorMessages() {}

    public static String getErrorString(Context context, int errorCode) {

        // Get a handle to resources, to allow the method to retrieve messages.
        Resources mResources = context.getResources();

        // Define a string to contain the error message
        String errorString;

        // Decide which error message to get, based on the error code.
        switch (errorCode) {
            case ConnectionResult.DEVELOPER_ERROR:
                errorString = mResources.getString(connection_error_misconfigured);
                break;

            case ConnectionResult.INTERNAL_ERROR:
                errorString = mResources.getString(connection_error_internal);
                break;

            case ConnectionResult.INVALID_ACCOUNT:
                errorString = mResources.getString(connection_error_invalid_account);
                break;

            case ConnectionResult.LICENSE_CHECK_FAILED:
                errorString = mResources.getString(connection_error_license_check_failed);
                break;

            case ConnectionResult.NETWORK_ERROR:
                errorString = mResources.getString(connection_error_network);
                break;

            case ConnectionResult.RESOLUTION_REQUIRED:
                errorString = mResources.getString(connection_error_needs_resolution);
                break;

            case ConnectionResult.SERVICE_DISABLED:
                errorString = mResources.getString(connection_error_disabled);
                break;

            case ConnectionResult.SERVICE_INVALID:
                errorString = mResources.getString(connection_error_invalid);
                break;

            case ConnectionResult.SERVICE_MISSING:
                errorString = mResources.getString(connection_error_missing);
                break;

            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                errorString = mResources.getString(connection_error_outdated);
                break;

            case ConnectionResult.SIGN_IN_REQUIRED:
                errorString = mResources.getString(
                        connection_error_sign_in_required);
                break;

            default:
                errorString = mResources.getString(connection_error_unknown);
                break;
        }

        // Return the error message
        return errorString;
    }
}
