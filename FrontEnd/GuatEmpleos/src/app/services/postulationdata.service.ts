import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostulationdataService {
  private postulationDataSubject = new Subject<any>();

  setPostulationData(data: any) {
    this.postulationDataSubject.next(data);
  }

  getPostulationData() {
    return this.postulationDataSubject.asObservable();
  }
}
