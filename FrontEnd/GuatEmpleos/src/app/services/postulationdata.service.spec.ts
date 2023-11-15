import { TestBed } from '@angular/core/testing';

import { PostulationdataService } from './postulationdata.service';

describe('PostulationdataService', () => {
  let service: PostulationdataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PostulationdataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
