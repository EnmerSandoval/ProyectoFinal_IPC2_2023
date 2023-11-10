import { Component } from '@angular/core';
import { JobService } from 'src/app/services/job.service';

@Component({
  selector: 'app-main-house',
  templateUrl: './main-house.component.html',
  styleUrls: ['./main-house.component.css']
})
export class MainHouseComponent {

  constructor(private jobService : JobService) { }

}