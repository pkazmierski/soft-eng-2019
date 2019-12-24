package pl.se.fitnessapp;

import android.content.Context;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.sigv4.CognitoUserPoolsAuthProvider;
import com.amazonaws.regions.Regions;

public class AppSyncClientSingleton {
    private static volatile AWSAppSyncClient awsAppSyncClient = null;

    private static AWSAppSyncClient getAWSAppSyncClient() {
        final Context ctx = App.getContext();
        final AWSConfiguration awsConfiguration = new AWSConfiguration(ctx);

        return AWSAppSyncClient.builder()
                .context(ctx)
                .awsConfiguration(awsConfiguration)
                .region(Regions.EU_CENTRAL_1)
                .cognitoUserPoolsAuthProvider(new CognitoUserPoolsAuthProvider() {
                    @Override
                    public String getLatestAuthToken() {
                        try {
                            return AWSMobileClient.getInstance().getTokens().getIdToken().getTokenString();
                        } catch (Exception e) {
                            if (e.getLocalizedMessage() != null)
                                Log.e("APPSYNC_ERROR", e.getLocalizedMessage());
                            else
                                Log.e("APPSYNC_ERROR", "localized message is null");

                            return e.getLocalizedMessage();
                        }
                    }
                }).build();
    }

    public static AWSAppSyncClient getInstance() {
        if (awsAppSyncClient == null)
            awsAppSyncClient = getAWSAppSyncClient();
        return awsAppSyncClient;
    }
}