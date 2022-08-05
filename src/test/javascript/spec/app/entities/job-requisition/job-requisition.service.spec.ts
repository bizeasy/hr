import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { JobRequisitionService } from 'app/entities/job-requisition/job-requisition.service';
import { IJobRequisition, JobRequisition } from 'app/shared/model/job-requisition.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

describe('Service Tests', () => {
  describe('JobRequisition Service', () => {
    let injector: TestBed;
    let service: JobRequisitionService;
    let httpMock: HttpTestingController;
    let elemDefault: IJobRequisition;
    let expectedResult: IJobRequisition | IJobRequisition[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(JobRequisitionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new JobRequisition(0, 0, 0, Gender.MALE, 0, 0, 'AAAAAAA', 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            requiredOnDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a JobRequisition', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            requiredOnDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            requiredOnDate: currentDate,
          },
          returnedFromService
        );

        service.create(new JobRequisition()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a JobRequisition', () => {
        const returnedFromService = Object.assign(
          {
            duration: 'BBBBBB',
            age: 1,
            gender: 'BBBBBB',
            experienceMonths: 1,
            experienceYears: 1,
            qualificationStr: 'BBBBBB',
            noOfResource: 1,
            requiredOnDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            requiredOnDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of JobRequisition', () => {
        const returnedFromService = Object.assign(
          {
            duration: 'BBBBBB',
            age: 1,
            gender: 'BBBBBB',
            experienceMonths: 1,
            experienceYears: 1,
            qualificationStr: 'BBBBBB',
            noOfResource: 1,
            requiredOnDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            requiredOnDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a JobRequisition', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
