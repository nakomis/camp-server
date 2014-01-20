package io.brooklyn.camp.spi.resolve;

import io.brooklyn.camp.spi.AbstractResource;
import io.brooklyn.camp.spi.PlatformComponentTemplate;
import io.brooklyn.camp.spi.pdp.Artifact;
import io.brooklyn.camp.spi.pdp.AssemblyTemplateConstructor;
import io.brooklyn.camp.spi.pdp.Service;

/** Matchers build up the AssemblyTemplate by matching against items in the deployment plan */
public interface PdpMatcher<T extends AbstractResource> {

    boolean accepts(Object deploymentPlanItem);
    T apply(Object deploymentPlanItem, AssemblyTemplateConstructor atc, PlatformComponentTemplate target);

    public abstract class ArtifactMatcher<U extends AbstractResource> implements PdpMatcher<U> {
        private String artifactType;
        public ArtifactMatcher(String artifactType) {
            this.artifactType = artifactType;
        }
        public boolean accepts(Object art) {
            return (art instanceof Artifact) && this.artifactType.equals( ((Artifact)art).getArtifactType() );
        }
    }
    
    public abstract class ServiceMatcher<U extends AbstractResource> implements PdpMatcher<U> {
        private String serviceType;
        public ServiceMatcher(String serviceType) {
            this.serviceType = serviceType;
        }
        public boolean accepts(Object svc) {
            return (svc instanceof Service) && this.serviceType.equals( ((Service)svc).getServiceType() );
        }
    }

}
