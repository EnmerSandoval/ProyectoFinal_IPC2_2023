import { Component } from '@angular/core';
import { Job } from 'src/app/model/Job';
import { JobService } from 'src/app/services/job.service';

@Component({
  selector: 'app-home-guest',
  templateUrl: './home-guest.component.html',
  styleUrls: ['./home-guest.component.css']
})
export class HomeGuestComponent {
  jobsList: Job[] = [];
    
  constructor (private jobService : JobService) { }

  ngOnInit(): void {
    this.jobService.getAllJobs().subscribe({
        next: (jobs: Job[]) => {
          this.jobsList = jobs;
        }
    });
    
  }

}
