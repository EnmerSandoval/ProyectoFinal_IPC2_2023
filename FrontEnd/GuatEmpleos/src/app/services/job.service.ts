import { Injectable } from '@angular/core';
import { Job } from '../model/Job';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from '../model/Category';

@Injectable({
  providedIn: 'root'
})
export class JobService {
  readonly url: string = 'http://localhost:8080/BackendGuateEmpleos_war_exploded';
  readonly URL2: string = 'http://localhost:8080/BackendGuateEmpleos_war_exploded';


  constructor(private httpClient : HttpClient) { }
  public getAllJobs(): Observable<Job[]> {
    const params = new HttpParams().set('flag', '1');
    return this.httpClient.get<Job[]>(`${this.url}/guest/mainGuestServlet`, { params});
  }

  public getAllJobsByCui(employerCui : number): Observable<Job[]> {
    const params = new HttpParams().set('flag', '4').set('employerCui', employerCui);
    return this.httpClient.get<Job[]>(`${this.url}/guest/mainGuestServlet`, { params});
  }

  public deleteJobOffert(numberJobOffert : number){
    const params = new HttpParams().set('flag', '5').set('numberJobOffert', numberJobOffert);
    return this.httpClient.get(`${this.url}/guest/mainGuestServlet`, { params});
  }
  
  public getAllCategories(): Observable<Category[]>{
    const params = new HttpParams().set('flag', '1');
    return this.httpClient.get<Category[]>(`${this.url}/categories/categoriesServlet`, { params});
  }

  public createJobOffert(newJob: Job, flag: string = '1') {
    const params = new HttpParams().set('flag', flag);
    return this.httpClient.post<Job>(`${this.url}/jobs/jobServlet`, newJob, { params });
  }

  public editerJobOffert(newJob : Job): Observable<Job>{
    const params = new HttpParams().set('flag', '2');
    return this.httpClient.post<Job>(`${this.url}/jobs/jobServlet`, newJob, { params });
  }

  public getJobOffert(number : number, flag: string = '3'): Observable<Job>{
    const params = new HttpParams().set('flag', flag)
    .set('number', number);
    return this.httpClient.post<Job>(`${this.url}/jobs/jobServlet`, number, { params});
  }
}
