import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EquipmentUsageLogService } from 'app/entities/equipment-usage-log/equipment-usage-log.service';
import { IEquipmentUsageLog, EquipmentUsageLog } from 'app/shared/model/equipment-usage-log.model';

describe('Service Tests', () => {
  describe('EquipmentUsageLog Service', () => {
    let injector: TestBed;
    let service: EquipmentUsageLogService;
    let httpMock: HttpTestingController;
    let elemDefault: IEquipmentUsageLog;
    let expectedResult: IEquipmentUsageLog | IEquipmentUsageLog[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EquipmentUsageLogService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new EquipmentUsageLog(0, currentDate, currentDate, 'AAAAAAA');
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

      it('should create a EquipmentUsageLog', () => {
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

        service.create(new EquipmentUsageLog()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EquipmentUsageLog', () => {
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

      it('should return a list of EquipmentUsageLog', () => {
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

      it('should delete a EquipmentUsageLog', () => {
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
