import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FileService } from 'src/app/services/file.service';
import { User } from 'src/app/model/User';

@Component({
  selector: 'app-upload-cv',
  templateUrl: './upload-cv.component.html',
  styleUrls: ['./upload-cv.component.css']
})
export class UploadCvComponent {
  error: boolean = false;
  selectedFile!: File;
  fileUploaded: boolean = false;
  user ! : User;

  constructor(private router: Router, private fileService: FileService) { 
    this.user = new User();
  }

  uploadToServer(): void {
    this.fileUploaded = false;
    this.cuiUser();
    if (this.selectedFile != null) {
      this.fileService.uploadFile(this.user, this.selectedFile).subscribe({
        next: () => {
          this.fileUploaded = true;
        }
      });
    }
    this.router.navigate(['/select-categories']);
  }

  cuiUser(){
   this.user = this.getCurrentUserFromLocalStorage() ?? {} as User;
  }

  processFile(event: Event): void {
    let files = (event.target as HTMLInputElement).files;
    if (files != null) {
      this.selectedFile = files[0];
    }
  }

  getCurrentUserFromLocalStorage(): User | null {
    const storedUser = localStorage.getItem('user');
    return storedUser ? JSON.parse(storedUser) : null;
  }
}
