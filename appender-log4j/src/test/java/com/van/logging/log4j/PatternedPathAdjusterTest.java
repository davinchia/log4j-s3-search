package com.van.logging.log4j;

import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest({PatternedPathAdjuster.class})
public class PatternedPathAdjusterTest extends TestCase {

    public void testPlainPath() {
        PatternedPathAdjuster adjuster = new PatternedPathAdjuster();
        assertEquals("log/messages/myapp", adjuster.adjustPath("log/messages/myapp"));
    }

    public void testNullForNull() {
        PatternedPathAdjuster adjuster = new PatternedPathAdjuster();
        assertNull(adjuster.adjustPath(null));
    }

    public void testBlankForBlank() {
        PatternedPathAdjuster adjuster = new PatternedPathAdjuster();
        assertEquals("", adjuster.adjustPath(""));
    }

    public void testExpandDate() {
        PatternedPathAdjuster adjuster = new PatternedPathAdjuster();
        mockStatic(System.class);
        expect(System.currentTimeMillis()).andReturn(1598244406898L); // 2020-08-23 21:46:46
        replayAll();

        String adjusted = adjuster.adjustPath("logs/%d{yyyy_MM_dd_HH_mm_ss}");
        assertEquals("logs/2020_08_23_21_46_46", adjusted);
        verifyAll();
    }
}
