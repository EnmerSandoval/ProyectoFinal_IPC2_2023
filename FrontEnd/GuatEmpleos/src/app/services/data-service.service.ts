import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User }  from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class DataServiceService {

  constructor(private httpClient: HttpClient) { }

  readonly API_URL = "http://localhost:8080/BackendGuateEmpleos_war_exploded";
  readonly DOWNLOAD_URL = "http://localhost:8080/BackendGuateEmpleos_war_exploded";

  public uploadFile(fileToUpload: File): Observable<void> {
    let formData: FormData = new FormData();
    formData.append("datafile", fileToUpload, fileToUpload.name);
    return this.httpClient.post<void>(`${this.API_URL}/readDataServlet`, formData);
  }

}
