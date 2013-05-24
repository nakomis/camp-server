package io.brooklyn.camp.test.platform;

import io.brooklyn.camp.BasicCampPlatform;
import io.brooklyn.camp.spi.ApplicationComponentTemplate;
import io.brooklyn.camp.spi.AbstractResource;
import io.brooklyn.camp.spi.PlatformComponentTemplate;
import io.brooklyn.camp.spi.collection.ResolveableLink;
import io.brooklyn.camp.test.mock.web.MockAppServerComponent;
import io.brooklyn.camp.test.mock.web.MockWarComponent;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BasicCampPlatformTest {

    @Test
    public void testEmptyPlatform() {
        BasicCampPlatform p = new BasicCampPlatform();
        assertResourceFieldsNotNull(p.root());
        Assert.assertEquals(p.platformComponentTemplates().links().size(), 0);
    }        

    @Test
    public void testWebPctSetup() {
        BasicCampPlatform p = new BasicCampPlatform();
        p.platformComponentTemplates().add(MockAppServerComponent.TEMPLATE);
        
        assertResourceFieldsNotNull(p.root());
        
        Assert.assertEquals(p.platformComponentTemplates().links().size(), 1);
        ResolveableLink<PlatformComponentTemplate> l = p.platformComponentTemplates().links().get(0);
        assertLinkFieldsNotNull(l);
        Assert.assertEquals(l.getName(), MockAppServerComponent.class.getCanonicalName());
        
        PlatformComponentTemplate pct = l.resolve();
        assertResourceFieldsNotNull(pct);
    }        

    @Test
    public void testWarActSetup() {
        BasicCampPlatform p = new BasicCampPlatform();
        p.applicationComponentTemplates().add(MockWarComponent.TEMPLATE);
        
        assertResourceFieldsNotNull(p.root());
        
        Assert.assertEquals(p.platformComponentTemplates().links().size(), 0);
        Assert.assertEquals(p.applicationComponentTemplates().links().size(), 1);
        ResolveableLink<ApplicationComponentTemplate> l = p.applicationComponentTemplates().links().get(0);
        assertLinkFieldsNotNull(l);
        Assert.assertEquals(l.getName(), MockWarComponent.class.getCanonicalName());
        
        ApplicationComponentTemplate act = l.resolve();
        assertResourceFieldsNotNull(act);
    }        


    public static void assertLinkFieldsNotNull(ResolveableLink<?> x) {
        Assert.assertNotNull(x.getId());
        Assert.assertNotNull(x.getName());
    }

    public static void assertResourceFieldsNotNull(AbstractResource x) {
        Assert.assertNotNull(x.getId());
        Assert.assertNotNull(x.getType());
        Assert.assertNotNull(x.getCreated());
        Assert.assertNotNull(x.getName());
        Assert.assertNotNull(x.getTags());
    }
    
}
