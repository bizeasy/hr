import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CommunicationEventService } from 'app/entities/communication-event/communication-event.service';
import { ICommunicationEvent, CommunicationEvent } from 'app/shared/model/communication-event.model';

describe('Service Tests', () => {
  describe('CommunicationEvent Service', () => {
    let injector: TestBed;
    let service: CommunicationEventService;
    let httpMock: HttpTestingController;
    let elemDefault: ICommunicationEvent;
    let expectedResult: ICommunicationEvent | ICommunicationEvent[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CommunicationEventService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CommunicationEvent(
        0,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            entryDate: currentDate.format(DATE_TIME_FORMAT),
            dateStarted: currentDate.format(DATE_TIME_FORMAT),
            dateEnded: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CommunicationEvent', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            entryDate: currentDate.format(DATE_TIME_FORMAT),
            dateStarted: currentDate.format(DATE_TIME_FORMAT),
            dateEnded: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            entryDate: currentDate,
            dateStarted: currentDate,
            dateEnded: currentDate,
          },
          returnedFromService
        );

        service.create(new CommunicationEvent()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CommunicationEvent', () => {
        const returnedFromService = Object.assign(
          {
            entryDate: currentDate.format(DATE_TIME_FORMAT),
            subject: 'BBBBBB',
            content: 'BBBBBB',
            fromString: 'BBBBBB',
            toString: 'BBBBBB',
            ccString: 'BBBBBB',
            message: 'BBBBBB',
            dateStarted: currentDate.format(DATE_TIME_FORMAT),
            dateEnded: currentDate.format(DATE_TIME_FORMAT),
            info: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            entryDate: currentDate,
            dateStarted: currentDate,
            dateEnded: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CommunicationEvent', () => {
        const returnedFromService = Object.assign(
          {
            entryDate: currentDate.format(DATE_TIME_FORMAT),
            subject: 'BBBBBB',
            content: 'BBBBBB',
            fromString: 'BBBBBB',
            toString: 'BBBBBB',
            ccString: 'BBBBBB',
            message: 'BBBBBB',
            dateStarted: currentDate.format(DATE_TIME_FORMAT),
            dateEnded: currentDate.format(DATE_TIME_FORMAT),
            info: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            entryDate: currentDate,
            dateStarted: currentDate,
            dateEnded: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CommunicationEvent', () => {
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
