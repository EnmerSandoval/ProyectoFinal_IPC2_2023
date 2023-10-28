import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/User';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  private url: string = 'http://localhost:8080/BackendGuateEmpleos_war_exploded';

  constructor(private http: HttpClient) { }
  logIn(user : User) {
        return this.http.post<User>(`${this.url}/loginServlet`, user);
  }

  registerUser(user : User) {
    console.log("Se llego a register");
    return this.http.post<User>(`${this.url}/registerServlet`, user);
  }
}
