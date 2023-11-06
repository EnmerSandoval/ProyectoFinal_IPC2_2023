import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Employer } from '../model/Employer';

@Injectable({
  providedIn: 'root'
})
export class EmployerService {
  private url: string = 'http://localhost:8080/BackendGuateEmpleos_war_exploded';

  constructor(private http: HttpClient) { }

  descriptionEmployer(employer : Employer) {
    return this.http.post<Employer>(`${this.url}/employer/descriptionServlet`, employer);
  }
}
