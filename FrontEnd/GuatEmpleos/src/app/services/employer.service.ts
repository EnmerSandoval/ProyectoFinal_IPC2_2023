import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Employer } from '../model/Employer';
import { Card } from '../model/Card';

@Injectable({
  providedIn: 'root'
})
export class EmployerService {


  private url: string = 'http://localhost:8080/BackendGuateEmpleos_war_exploded';

  constructor(private http: HttpClient) { }

  descriptionEmployer(employer : Employer) {
    const params = new HttpParams().set('mission', employer.mission)
    .set('vision', employer.vision)
    .set('cui', employer.cui)
    .set('flag', '1');
    return this.http.get(`${this.url}/employer/descriptionServlet`, {params});
  }
  registerCardEmployer(card : Card) {
    const params = new HttpParams()
    .set('cuiEmployer', card.cuiEmployer)
    .set('numberCard', card.numberCard)
    .set('cvv', card.cvv)
    .set('nameChargedCard', card.nameChargeCard)
    .set('flag', '2');
    return this.http.get(`${this.url}/employer/descriptionServlet`, {params});
  }

}
