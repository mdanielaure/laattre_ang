export class User {
    id: number;
    username: string;
    password: string;
    firstName: string;
    lastName: string;
    token: string;
    roles: [];
    //shoppingCart: ShoppingCart;
    userShippingList: [];
    orderList: [];
    enabled: boolean;
    phone: string;
}