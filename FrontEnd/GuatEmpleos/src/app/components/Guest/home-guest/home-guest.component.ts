import { Component } from '@angular/core';
import { Job } from 'src/app/model/Job';
import { JobService } from 'src/app/services/job.service';
import { User } from 'src/app/model/User';
import { Employer } from 'src/app/model/Employer';
import { GuestService } from 'src/app/services/guest.service';

@Component({
  selector: 'app-home-guest',
  templateUrl: './home-guest.component.html',
  styleUrls: ['./home-guest.component.css']
})
export class HomeGuestComponent {
  jobsList: Job[] = [];
  employer! : Employer;
  showModalData! : Employer;
    
  constructor (private jobService : JobService, private guestService : GuestService, private router : Route) {
    this.employer = new Employer();
    this.showModalData = new Employer();
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
        console.log("CUI: " + this.showModalData.cui);
        console.log("Nombre: " + this.showModalData.name);
      }
    });
    (window as any).$('#viewEmployer').modal('show');
  }

  showModalJobOffert(){
    (window as any).$('#viewJobOffert').modal('show');
  }

  hideModal(){
    (window as any).$('#viewEmployer').modal('hide');
    this.ngOnInit();
  }

  redirect(){
    this.router.navigate(['/login']);
  }
}
