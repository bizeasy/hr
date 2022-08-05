import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { FacilityUsageLogService } from 'app/entities/facility-usage-log/facility-usage-log.service';
import { IFacilityUsageLog, FacilityUsageLog } from 'app/shared/model/facility-usage-log.model';

describe('Service Tests', () => {
  describe('FacilityUsageLog Service', () => {
    let injector: TestBed;
    let service: FacilityUsageLogService;
    let httpMock: HttpTestingController;
    let elemDefault: IFacilityUsageLog;
    let expectedResult: IFacilityUsageLog | IFacilityUsageLog[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FacilityUsageLogService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new FacilityUsageLog(0, currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fromTime: currentDate.format(DATE_TIME_FORMAT),
            toTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a FacilityUsageLog', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fromTime: currentDate.format(DATE_TIME_FORMAT),
            toTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromTime: currentDate,
            toTime: currentDate,
          },
          returnedFromService
        );

        service.create(new FacilityUsageLog()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FacilityUsageLog', () => {
        const returnedFromService = Object.assign(
          {
            fromTime: currentDate.format(DATE_TIME_FORMAT),
            toTime: currentDate.format(DATE_TIME_FORMAT),
            remarks: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromTime: currentDate,
            toTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FacilityUsageLog', () => {
        const returnedFromService = Object.assign(
          {
            fromTime: currentDate.format(DATE_TIME_FORMAT),
            toTime: currentDate.format(DATE_TIME_FORMAT),
            remarks: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromTime: currentDate,
            toTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a FacilityUsageLog', () => {
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
