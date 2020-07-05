package dynamicProxy;

public class UserServiceImpl implements UserService {
    @Override
    public void select() {
        System.out.println("select 'are you OK?'");
    }

    @Override
    public void update() {
        System.out.println("drop table and run away");
    }
}
