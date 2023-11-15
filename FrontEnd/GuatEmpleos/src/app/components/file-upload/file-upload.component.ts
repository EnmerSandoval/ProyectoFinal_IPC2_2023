import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FileService } from 'src/app/services/file.service';
import { User } from 'src/app/model/User';
import { DataServiceService } from 'src/app/services/data-service.service';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent {
  error: boolean = false;
  selectedFile!: File;
  fileUploaded: boolean = false;

  constructor(private router: Router, private dataService : DataServiceService) { 
    
  }

  uploadToServer(): void {
    this.fileUploaded = false;
    if (this.selectedFile != null) {
      this.dataService.uploadFile(this.selectedFile).subscribe({
        next: () => {
          this.fileUploaded = true;
        }
      });
    }
  }


  processFile(event: Event): void {
    let files = (event.target as HTMLInputElement).files;
    if (files != null) {
      this.selectedFile = files[0];
    }
  }

}
