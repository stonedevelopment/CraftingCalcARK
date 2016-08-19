package arc.resource.calculator.model;

public class ComplexResource extends CraftableResource {
    private long engramId;

    public ComplexResource(CraftableResource resource, long engramId) {
        super(resource, resource.getQuantity());

        this.engramId = engramId;
    }
}
