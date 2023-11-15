import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ApplicantService } from 'src/app/services/applicant.service';
import { User } from 'src/app/model/User';
import { JobService } from 'src/app/services/job.service';
import { Job } from 'src/app/model/Job';
import { Employer } from 'src/app/model/Employer';
import { GuestService } from 'src/app/services/guest.service';

@Component({
  selector: 'app-home-applicant',
  templateUrl: './home-applicant.component.html',
  styleUrls: ['./home-applicant.component.css']
})
export class HomeApplicantComponent {
  jobsList: Job[] = [];
  jobOffert! : Job;
  employer! : Employer;
  showModalData! : Employer;
  errorSelectedJobOffert : boolean = false;
  user! : User;
  suggestionOffert : boolean = false;
  postulationReason! : string;
  jobOffertNumber! : number;
 
  constructor(private router: Router, private applicantService: ApplicantService, private jobService: JobService, private guestService : GuestService) { 
    this.employer = new Employer();
    this.showModalData = new Employer();
    this.jobOffert = new Job();
    this.user = new User();
    
  }

  ngOnInit(){
    this.cuiUser();
    this.applicantService.getRecomendedJobs(this.user.cui).subscribe({
      next: (jobs: Job[]) => {
        this.jobsList = jobs;
        this.suggestionOffert = true;
      }
  });
  }

  submitPostulation(){
    this.cuiUser();
    console.log(this.user.cui);
    console.log(this.jobOffertNumber);
    if (this.postulationReason.trim() === '') {
      alert('Razon de postulacion vacia');
      return;
    } else{ 
        this.applicantService.insertRequest(this.user.cui, this.jobOffertNumber, this.postulationReason).subscribe({ 
          
        });
        this.ngOnInit();
    }
  }


  showModal(cuiEmployer : number){
    this.employer.cui = cuiEmployer;
    this.guestService.getEmployer(this.employer).subscribe({
      next: (data: Employer) => {
        this.showModalData = data;
      }
    });
    (window as any).$('#viewEmployer').modal('show');
  }

  showModalPostulation(numberJobOffert: number){
    this.jobOffertNumber = numberJobOffert;
    (window as any).$('#postulationModal').modal('show');
  }

  showModalJobOffert(numberJobOffert : number){
    if (numberJobOffert != undefined && numberJobOffert > 0){
      this.errorSelectedJobOffert = false;
      this.jobOffert.numberJobOffert = numberJobOffert;
      
      this.guestService.getJobOffert(this.jobOffert).subscribe({
        next: (data: Job) => {
          this.jobOffert = data;
        }
      });
      (window as any).$('#viewJobOffert').modal('show');
    } else {
      this.errorSelectedJobOffert = true;
      console.log("Error: numberJobOffert is undefined or less than 1");
    }
  }

  cuiUser() {
    this.user = this.getCurrentUserFromLocalStorage() ?? {} as User;
  }

  getCurrentUserFromLocalStorage(): User | null {
    const storedUser = localStorage.getItem('user');
    return storedUser ? JSON.parse(storedUser) : null;
  }
}
