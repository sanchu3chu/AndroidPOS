package com.ricoh.pos;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MainMenuActivityTest {
    @Test
    public void shouldConfirmAppName() throws Exception {
        final MainMenuActivity activity = Robolectric.buildActivity(MainMenuActivity.class).get();
        String appName = activity.getResources().getString(R.string.app_name);
        assertEquals(appName, "RicohPOS");
    }
}
