import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { ApplicantService } from 'src/app/services/applicant.service';

@Component({
  selector: 'app-postulation-modal',
  templateUrl: './postulation-modal.component.html',
  styleUrls: ['./postulation-modal.component.css']
})
export class PostulationModalComponent {
  @Input() cuiApplicant!: number;
@Input() jobOffertNumber!: number;
  postulationReason! : string;
  isModalVisible : boolean = false;

  constructor(private applicantService : ApplicantService) { }

  showModal() {
    this.isModalVisible = true;
  }

  submitPostulation(){
    if (this.postulationReason.trim() === '') {
      alert('Razon de postulacion vacia');
      return;
    } else{ 
        this.applicantService.insertRequest(this.cuiApplicant, this.jobOffertNumber, this.postulationReason).subscribe({ 

        });
    }
  }
}
