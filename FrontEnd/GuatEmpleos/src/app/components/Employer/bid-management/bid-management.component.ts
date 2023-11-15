import { Component } from '@angular/core';
import { Job } from 'src/app/model/Job';
import { User } from 'src/app/model/User';
import { JobService } from 'src/app/services/job.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Category } from 'src/app/model/Category';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-bid-management',
  templateUrl: './bid-management.component.html',
  styleUrls: ['./bid-management.component.css']
})
export class BidManagementComponent {
  user! : User;
  jobsList: Job[] = [];
  job! : Job;
  registerForm! : FormGroup;
  newJob! : Job;
  updateJob! : Job;
  categoryList: Category[] = [];
  error : Boolean = false;
  editerForm! : FormGroup;
  editSuccesfull : Boolean = false;
  viewJobOffert! : Job;

  constructor(private jobService : JobService,  private formBuilder: FormBuilder, private datePipe : DatePipe) { 
    this.job = new Job();
    this.newJob = new Job();
    this.user = new User();
    this.updateJob = new Job(); 
    this.viewJobOffert = new Job();
  }

  ngOnInit(): void {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      this.user = JSON.parse(storedUser);
    }
    this.jobService.getAllJobsByCui(this.user.cui).subscribe({
      next: (jobs: Job[]) => {
        this.jobsList = jobs;
      }
  });
    this.validations();
    this.secondValidations();
  }

  validations(){
    this.registerForm = this.formBuilder.group({
      nameOfJobOffert: [null, [Validators.required, Validators.maxLength(1000)]],
      description: [null, [Validators.required, Validators.maxLength(4000)]],
      location: [null, [Validators.required, Validators.maxLength(1500)]],
      details: [null, [Validators.required, Validators.maxLength(1400)]],
      publicationDate: [null, Validators.required],
      applicationDeadline: [null, Validators.required],
      salary: [null, [Validators.required, Validators.maxLength(1000000)]],
      modality: [null, Validators.required],
      numberCategorie: [null, Validators.required]
    });
  }

  secondValidations(){
    this.editerForm = this.formBuilder.group({
      nameOfJobOffert: [null, [Validators.required, Validators.maxLength(1000)]],
      description: [null, [Validators.required, Validators.maxLength(4000)]],
      location: [null, [Validators.required, Validators.maxLength(1500)]],
      details: [null, [Validators.required, Validators.maxLength(1400)]],
      publicationDate: [null, Validators.required],
      applicationDeadline: [null, Validators.required],
      salary: [null, [Validators.required, Validators.maxLength(1000000)]],
      modality: [null, Validators.required],
      numberCategorie: [null, Validators.required]
    });
  }

  onClickLogOut(){
    localStorage.clear();
  }

  showModal(){
    this.uploadCategoryList();
    (window as any).$('#createJobOffert').modal('show');
  }

  createJobOffert(){
    this.newJob = { ...this.newJob, ...this.registerForm.value };
    this.newJob.cuiEmployer = this.user.cui;
    this.jobService.createJobOffert(this.newJob).subscribe( newJob => {
        this.error = false;
    }, error => {console.log(this.error);
      this.error = true;
    });
  }

  editerJobOffert(){
    this.editSuccesfull = false;
    this.updateJob = { ...this.updateJob, ...this.editerForm.value };
    console.log(this.updateJob.numberJobOffert);
    this.updateJob.cuiEmployer = this.user.cui;
    this.jobService.editerJobOffert(this.updateJob).subscribe( updateJob => {
      this.error = false;
      this.editSuccesfull = true;
    }, error => {console.log(this.error);
      this.error = true; 
    });
    this.ngOnInit();
  }



  showModalJobOffert(numberJobOffert : number){
    this.updateJob.numberJobOffert = numberJobOffert;
    this.uploadCategoryList();
    this.jobService.getJobOffert(numberJobOffert).subscribe({
      next: (job: Job) => {
        this.updateJob = job;
        if (this.updateJob.publicationDate && this.updateJob.publicationDate != undefined) {
          const formattedDate = this.formatDate(this.updateJob.publicationDate);
          this.editerForm.get('publicationDate')?.setValue(formattedDate);
        }
  
        if (this.updateJob.applicationDeadline && this.updateJob.applicationDeadline != undefined) {
          const formattedDate2 = this.formatDate(this.updateJob.applicationDeadline);
          this.editerForm.get('applicationDeadline')?.setValue(formattedDate2);
        }

        this.editerForm.patchValue({
          nameOfJobOffert: this.updateJob.nameOfJobOffert,
          description: this.updateJob.description,
          location: this.updateJob.location,
          details: this.updateJob.details,
          salary: this.updateJob.salary,
          modality: this.updateJob.modality,
          numberCategorie: this.updateJob.numberCategorie
        });
      }
    });
    (window as any).$('#editJobOffert').modal('show');
  }

  showModalViewJobOffert(numberJobOffert : number){
    this.viewJobOffert.numberJobOffert = numberJobOffert;
    this.uploadCategoryList();
    this.jobService.getJobOffert(numberJobOffert).subscribe({
      next: (job: Job) => {
        this.viewJobOffert = job;
        if (this.viewJobOffert.publicationDate && this.viewJobOffert.publicationDate != undefined) {
          const formattedDate = this.formatDate(this.viewJobOffert.publicationDate);
          this.editerForm.get('publicationDate')?.setValue(formattedDate);
        }
  
        if (this.viewJobOffert.applicationDeadline && this.viewJobOffert.applicationDeadline != undefined) {
          const formattedDate2 = this.formatDate(this.viewJobOffert.applicationDeadline);
          this.editerForm.get('applicationDeadline')?.setValue(formattedDate2);
        }

        this.editerForm.patchValue({
          nameOfJobOffert: this.viewJobOffert.nameOfJobOffert,
          description: this.viewJobOffert.description,
          location: this.viewJobOffert.location,
          details: this.viewJobOffert.details,
          salary: this.viewJobOffert.salary,
          modality: this.viewJobOffert.modality,
          numberCategorie: this.viewJobOffert.numberCategorie
        });
      }
    });
    (window as any).$('#viewJobOffert').modal('show');
  }

  deleteJobOffert(numberJobOffert : number){
      this.jobService.deleteJobOffert(numberJobOffert).subscribe({
      });
      this.ngOnInit();
  }

  uploadCategoryList(){
    this.jobService.getAllCategories().subscribe({
      next: (categories: Category[]) => {
        this.categoryList = categories;
        console.log(this.categoryList.toString);
      }
    });
  }

  private formatDate(date: Date | string): string {
    if (typeof date === 'string') {
      const dateParts = date.split(' ');
   
      const monthMapping: { [key: string]: number } = {
        'ene': 0, 'feb': 1, 'mar': 2, 'abr': 3, 'may': 4, 'jun': 5, 'jul': 6, 'ago': 7, 'sep': 8, 'oct': 9, 'nov': 10, 'dic': 11
      };
      
      const month = monthMapping[dateParts[0].toLowerCase()];
      const day = parseInt(dateParts[1], 10);
      const year = parseInt(dateParts[2], 10);
  
      date = new Date(year, month, day);
    }
  
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    return `${year}-${month}-${day}`;
  }
  
  closeUpdate(){
    this.editSuccesfull = false;
    (window as any).$('#editJobOffert').modal('hide');
  }

}
