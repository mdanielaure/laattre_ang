import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user';
import { config } from '../config';



@Injectable({ providedIn: 'root' })
export class UserService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<User[]>(`${config.apiUrl}/users`);
    }

    register(user: any) {
        return this.http.post(`${config.apiUrl}/user/registration`, user);
    }

    delete(id: number) {
        return this.http.delete(`${config.apiUrl}/users/${id}`);
    }

    confirmRegistration(token: any){
        return this.http.get(`${config.apiUrl}/user/registrationConfirm`, {params:{token}});
    }

    resendToken(oldToken: any){
        return this.http.get(`${config.apiUrl}/user/resendRegistrationToken`, {params:{token:oldToken}});
    }

    getOrderDetail(id: any, userEmail: string){
        return this.http.get(`${config.apiUrl}/user/orderDetail`, {params:{id, userEmail}})
    }
}