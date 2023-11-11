import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Employer } from '../model/Employer';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GuestService {

  private url: string = 'http://localhost:8080/BackendGuateEmpleos_war_exploded';

  constructor(private httpClient: HttpClient) { }

  getEmployer(employer: Employer): Observable<Employer> {
    const params = new HttpParams().set('flag', '2').set('cui', employer.cui);
    return this.httpClient.get<Employer>(`${this.url}/guest/mainGuestServlet`, { params });
  }
}

