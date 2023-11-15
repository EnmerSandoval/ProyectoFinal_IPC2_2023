import { Component } from '@angular/core';
import { Job } from 'src/app/model/Job';
import { JobService } from 'src/app/services/job.service';
import { User } from 'src/app/model/User';
import { Employer } from 'src/app/model/Employer';
import { GuestService } from 'src/app/services/guest.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home-guest',
  templateUrl: './home-guest.component.html',
  styleUrls: ['./home-guest.component.css']
})
export class HomeGuestComponent {
  jobsList: Job[] = [];
  jobOffert! : Job;
  employer! : Employer;
  showModalData! : Employer;
  errorSelectedJobOffert : boolean = false;
    
  constructor (private jobService : JobService, private guestService : GuestService, private router : Router) {
    this.employer = new Employer();
    this.showModalData = new Employer();
    this.jobOffert = new Job();
  }

  ngOnInit(): void {
    this.jobService.getAllJobs().subscribe({
        next: (jobs: Job[]) => {
          this.jobsList = jobs;
        }
    });
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

  hideModal(){
    (window as any).$('#viewJobOffert').modal('hide');
    this.ngOnInit();
  }

  redirect(){
    this.router.navigate(['/login']);
  }
}
