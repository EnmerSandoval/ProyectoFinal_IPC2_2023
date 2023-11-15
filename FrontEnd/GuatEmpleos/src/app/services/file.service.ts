import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(private httpClient: HttpClient) { }

  readonly API_URL = "http://localhost:8080/BackendGuateEmpleos_war_exploded";
  readonly DOWNLOAD_URL = "http://localhost:8080/BackendGuateEmpleos_war_exploded";

  public uploadFile(user: User, fileToUpload: File): Observable<void> {
    let formData: FormData = new FormData();
    formData.append("datafile", fileToUpload, fileToUpload.name);

    return this.httpClient.post<void>(`${this.API_URL}/file/readCv`+ "?cui=" + user.cui, formData);
  }

  public downloadFile(user: User): string {
    return this.DOWNLOAD_URL + "?cui=" + user.cui;
  }
}
