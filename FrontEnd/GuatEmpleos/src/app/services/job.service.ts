import { Injectable } from '@angular/core';
import { Job } from '../model/Job';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JobService {
  readonly url: string = 'http://localhost:8080/BackendGuateEmpleos_war_exploded';

  constructor(private httpClient : HttpClient) { }
  public getAllJobs(): Observable<Job[]> {
    const params = new HttpParams().set('flag', '1');
    return this.httpClient.get<Job[]>(`${this.url}/guest/mainGuestServlet`, { params});
  }
  
}
