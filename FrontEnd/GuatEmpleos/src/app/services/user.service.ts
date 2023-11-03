import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { User } from '../model/User';
import { Token } from '../model/token';
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
    return this.http.post<User>(`${this.url}/registerServlet`, user);
  }

  forgotPassword(user : User) {
    const params = new HttpParams().set('email', user.email).set('forgotPassword', '1');
    return this.http.get(`${this.url}/forgotPassword`, { params, responseType: 'text' });
  }

  verifyToken(token : Token){
    return this.http.post<Token>(`${this.url}/forgotPassword`, token);
  }

  logOut() {
    localStorage.removeItem('user');
  }
}
