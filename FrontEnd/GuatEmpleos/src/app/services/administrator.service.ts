import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Data } from '../model/Data';
import { Category } from '../model/Category';

@Injectable({
  providedIn: 'root'
})
export class AdministratorService {
  
  private url: string = 'http://localhost:8080/BackendGuateEmpleos_war_exploded';

  constructor(private httpClient : HttpClient) { }

 
  public getViews(data : Data): Observable<Data> {
    const params = new HttpParams().set('flag', '1');
    return this.httpClient.get<Data>(`${this.url}/admin/administratorDataServlet`, {params});
  }

  public insertCategories(category : Category): Observable<Category[]> {
    const params = new HttpParams().set('flag', '2').set('category', JSON.stringify(category));
    return this.httpClient.get<Category[]>(`${this.url}/admin/administratorDataServlet`, {params});
  }

  public getAllCategories(): Observable<Category[]>{
    const params = new HttpParams().set('flag', '3');
    return this.httpClient.get<Category[]>(`${this.url}/admin/administratorDataServlet`, {params});
  }

  public updateCategories(category : Category): Observable<Category[]> {
    const params = new HttpParams().set('flag', '4').set('category', JSON.stringify(category));
    return this.httpClient.get<Category[]>(`${this.url}/admin/administratorDataServlet`, {params});
  }
}
