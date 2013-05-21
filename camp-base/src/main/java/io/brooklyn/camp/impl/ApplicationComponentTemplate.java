package io.brooklyn.camp.impl;


/** Holds the metadata (name, description, etc) for a PCT
 * as well as fields pointing to behaviour (eg creation of PlatformComponent).
 * <p>
 * See {@link BasicResource} for more general information.
 */
public class ApplicationComponentTemplate extends BasicResource {

    public static final String CAMP_TYPE = "ApplicationComponentTemplate";
    static { assert CAMP_TYPE.equals(ApplicationComponentTemplate.class.getSimpleName()); }
    
    /** Use {@link #builder()} to create */
    protected ApplicationComponentTemplate() {}

    
    // no fields beyond basic resource
    
    
    // builder
    
    public static Builder<? extends ApplicationComponentTemplate> builder() {
        return new Builder<ApplicationComponentTemplate>(CAMP_TYPE);
    }
    
    public static class Builder<T extends ApplicationComponentTemplate> extends BasicResource.Builder<T> {
        
        protected Builder(String type) { super(type); }
        
        @SuppressWarnings("unchecked")
        protected T createResource() { return (T) new ApplicationComponentTemplate(); }
        
//        public Builder<T> foo(String x) { instance().setFoo(x); return this; }
    }

}
