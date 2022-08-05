import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PartyResumeService } from 'app/entities/party-resume/party-resume.service';
import { IPartyResume, PartyResume } from 'app/shared/model/party-resume.model';

describe('Service Tests', () => {
  describe('PartyResume Service', () => {
    let injector: TestBed;
    let service: PartyResumeService;
    let httpMock: HttpTestingController;
    let elemDefault: IPartyResume;
    let expectedResult: IPartyResume | IPartyResume[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PartyResumeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PartyResume(0, 'AAAAAAA', currentDate, 'image/png', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            resumeDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PartyResume', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            resumeDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            resumeDate: currentDate,
          },
          returnedFromService
        );

        service.create(new PartyResume()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PartyResume', () => {
        const returnedFromService = Object.assign(
          {
            text: 'BBBBBB',
            resumeDate: currentDate.format(DATE_FORMAT),
            fileAttachment: 'BBBBBB',
            attachmentUrl: 'BBBBBB',
            mimeType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            resumeDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PartyResume', () => {
        const returnedFromService = Object.assign(
          {
            text: 'BBBBBB',
            resumeDate: currentDate.format(DATE_FORMAT),
            fileAttachment: 'BBBBBB',
            attachmentUrl: 'BBBBBB',
            mimeType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            resumeDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PartyResume', () => {
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
