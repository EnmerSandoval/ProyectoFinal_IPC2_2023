import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Category } from '../model/Category';
import { Observable } from 'rxjs';
import { Job } from '../model/Job';

@Injectable({
  providedIn: 'root'
})
export class ApplicantService {

  readonly url: string = 'http://localhost:8080/BackendGuateEmpleos_war_exploded';

  constructor(private httpClient : HttpClient) { }


  public insertOwnCategory(categoryList : Category[], numberCui : number){
    const params = new HttpParams().set('flag', '1')
    .set('numberCui', numberCui);
    return this.httpClient.post(this.url + '/applicantServlet', categoryList, {params});
  }

  public getRecomendedJobs(numberCui : number): Observable<Job[]> {
    const params = new HttpParams().set('flag', '2').set('numberCui', numberCui);
    return this.httpClient.post<Job[]>(`${this.url}/applicantServlet`, null, { params});
  }

  public insertRequest(numberCui : number, numberJobOffert : number, postulationReason : string){
    const params = new HttpParams().set('flag', '3')
    .set('numberCui', numberCui)
    .set('numberJobOffert', numberJobOffert)
    .set('postulationReason', postulationReason);
    return this.httpClient.post(this.url + '/applicantServlet', null, {params});
  }

}
