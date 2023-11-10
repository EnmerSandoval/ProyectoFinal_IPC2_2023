import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Data } from '../model/Data';
import { Category } from '../model/Category';
import { Commission } from '../model/Commission';

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
    console.log("inser category" + category.nameCategory);
    const params = new HttpParams().set('flag', '2')
    .set('nameCategory', category.nameCategory)
    .set('description', category.description);
    return this.httpClient.get<Category[]>(`${this.url}/admin/administratorDataServlet`, {params});
  }

  public getAllCategories(): Observable<Category[]>{
    const params = new HttpParams().set('flag', '3');
    return this.httpClient.get<Category[]>(`${this.url}/admin/administratorDataServlet`, {params});
  }

  public updateCategories(category : Category): Observable<Category[]> {
    const params = new HttpParams().set('flag', '4')
    . set('numberCategory', category.numberCategory)
    . set('nameCategory', category.nameCategory)
    . set('description', category.description);
    return this.httpClient.get<Category[]>(`${this.url}/admin/administratorDataServlet`, {params});
  }

  public selectCategory(category : Category): Observable<Category> { 
    const params = new HttpParams().set('flag', '5')
    .set ('numberCategory', category.numberCategory);
    return this.httpClient.get<Category>(`${this.url}/admin/administratorDataServlet`, {params});
  }

  public getCommissions(): Observable<Commission>{
    const params = new HttpParams().set('flag', '6');
    return this.httpClient.get<Commission>(`${this.url}/admin/administratorDataServlet`, {params});
  }

  public updateCommission(commission : Commission): Observable<Commission>{
    const params = new HttpParams().set('flag', '7')
    .set('numberCommission', 1)
    .set('amount', commission.amount);
    console.log("Update commision: " + commission.amount);
    return this.httpClient.get<Commission>(`${this.url}/admin/administratorDataServlet`, {params});
  }
  
}
