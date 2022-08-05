import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EmplPositionReportingStructService } from 'app/entities/empl-position-reporting-struct/empl-position-reporting-struct.service';
import { IEmplPositionReportingStruct, EmplPositionReportingStruct } from 'app/shared/model/empl-position-reporting-struct.model';

describe('Service Tests', () => {
  describe('EmplPositionReportingStruct Service', () => {
    let injector: TestBed;
    let service: EmplPositionReportingStructService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmplPositionReportingStruct;
    let expectedResult: IEmplPositionReportingStruct | IEmplPositionReportingStruct[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EmplPositionReportingStructService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new EmplPositionReportingStruct(0, currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fromDate: currentDate.format(DATE_FORMAT),
            thruDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EmplPositionReportingStruct', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fromDate: currentDate.format(DATE_FORMAT),
            thruDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            thruDate: currentDate,
          },
          returnedFromService
        );

        service.create(new EmplPositionReportingStruct()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EmplPositionReportingStruct', () => {
        const returnedFromService = Object.assign(
          {
            fromDate: currentDate.format(DATE_FORMAT),
            thruDate: currentDate.format(DATE_FORMAT),
            comments: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            thruDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EmplPositionReportingStruct', () => {
        const returnedFromService = Object.assign(
          {
            fromDate: currentDate.format(DATE_FORMAT),
            thruDate: currentDate.format(DATE_FORMAT),
            comments: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            thruDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EmplPositionReportingStruct', () => {
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
