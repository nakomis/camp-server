package io.brooklyn.camp.rest.util;

import io.brooklyn.camp.CampPlatform;
import io.brooklyn.camp.dto.PlatformComponentTemplateDto;
import io.brooklyn.camp.dto.PlatformDto;
import io.brooklyn.camp.impl.BasicResource;
import io.brooklyn.camp.impl.PlatformComponentTemplate;
import io.brooklyn.camp.impl.PlatformRootSummary;
import io.brooklyn.camp.rest.resource.AbstractCampRestResource;
import io.brooklyn.camp.rest.resource.PlatformComponentTemplateRestResource;
import io.brooklyn.camp.rest.resource.PlatformRestResource;

import java.util.Map;

import javax.ws.rs.Path;

import brooklyn.util.collections.MutableMap;
import brooklyn.util.net.Urls;

import com.google.common.base.Function;

public class DtoFactory {

    private CampPlatform platform;
    private String uriBase;
    
    private UriFactory uriFactory;

    public DtoFactory(CampPlatform campPlatform, String uriBase) {
        this.platform = campPlatform;
        this.uriBase = uriBase;
        
        uriFactory = new UriFactory();
        uriFactory.registerIdentifiableRestResource(PlatformRootSummary.class, PlatformRestResource.class);
        uriFactory.registerIdentifiableRestResource(PlatformComponentTemplate.class, PlatformComponentTemplateRestResource.class);
    }

    public CampPlatform getPlatform() {
        return platform;
    }

    public UriFactory getUriFactory() {
        return uriFactory;
    }
    
    public String uri(BasicResource x) {
        return getUriFactory().uri(x);
    }
        
    public String uri(Class<? extends BasicResource> targetType, String id) {
        return getUriFactory().uri(targetType, id);
    }

    public PlatformComponentTemplateDto adapt(PlatformComponentTemplate platformComponentTemplate) {
        return PlatformComponentTemplateDto.newInstance(this, platformComponentTemplate);
    }

    public PlatformDto adapt(PlatformRootSummary root) {
        return PlatformDto.newInstance(this, root);
    }

    public class UriFactory {
        /** registry of generating a URI given an object */
        Map<Class<?>,Function<Object,String>> registryResource = new MutableMap<Class<?>, Function<Object,String>>();
        /** registry of generating a URI given an ID */
        Map<Class<?>,Function<String,String>> registryId = new MutableMap<Class<?>, Function<String,String>>();

        /** registers a function which generates a URI given a type; note that this method cannot be used for links */
        @SuppressWarnings("unchecked")
        public synchronized <T> void registerResourceUriFunction(Class<T> type, Function<T,String> fnUri) {
            registryResource.put(type, (Function<Object, String>) fnUri);
        }

        /** registers a type to generate a URI which concatenates the given base with the
         * result of the given function to generate an ID against an object of the given type */
        public synchronized <T> void registerIdentityFunction(Class<T> type, final String resourceTypeUriBase, final Function<T,String> fnIdentity) {
            final Function<String,String> fnUriFromId = new Function<String,String>() {
                public String apply(String id) {
                    return Urls.mergePaths(resourceTypeUriBase, id);
                }
            };
            registryId.put(type, (Function<String, String>) fnUriFromId);
            registerResourceUriFunction(type, new Function<T,String>() {
                public String apply(T input) {
                    return fnUriFromId.apply(fnIdentity.apply(input));
                }
            });
        }

        /** registers a CAMP Resource type against a RestResource, generating the URI
         * by concatenating the @Path annotation on the RestResource with the ID of the CAMP resource */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public synchronized <T extends BasicResource> void registerIdentifiableRestResource(Class<T> type, Class<? extends AbstractCampRestResource> restResource) {
            registerIdentityFunction(type, 
                    Urls.mergePaths(uriBase, restResource.getAnnotation(Path.class).value()),
                    (Function) CampRestGuavas.IDENTITY_OF_REST_RESOURCE);
        }
        
        public String uri(Class<? extends BasicResource> targetType, String id) {
            return registryId.get(targetType).apply(id);
        }

        public String uri(BasicResource x) {
            return registryResource.get(x.getClass()).apply(x);
        }
    }

}