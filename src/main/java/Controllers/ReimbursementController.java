package Controllers;

public class ReimbursementController {
    private static UserServices userServ = new UserServicesImpl();

    // GET to /pets
    public static void getReimbursements(Context ctx) {
        ctx.json(userServ.viewAvailablePets());
    }

    // GET to /pets/{id} where {id} is the ID of the pet
    public static void getPetById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Pet pet = userServ.getPetById(id);
        if (pet != null)
            ctx.json(pet);
        else
            ctx.status(HttpCode.NOT_FOUND); // 404 not found
    }

}
