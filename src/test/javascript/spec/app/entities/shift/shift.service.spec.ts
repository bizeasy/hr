import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ShiftService } from 'app/entities/shift/shift.service';
import { IShift, Shift } from 'app/shared/model/shift.model';

describe('Service Tests', () => {
  describe('Shift Service', () => {
    let injector: TestBed;
    let service: ShiftService;
    let httpMock: HttpTestingController;
    let elemDefault: IShift;
    let expectedResult: IShift | IShift[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ShiftService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Shift(0, 'AAAAAAA', currentDate, currentDate, 0, 0);
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

      it('should create a Shift', () => {
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

        service.create(new Shift()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Shift', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            fromTime: currentDate.format(DATE_TIME_FORMAT),
            toTime: currentDate.format(DATE_TIME_FORMAT),
            workingHrs: 1,
            monthlyPaidLeaves: 1,
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

      it('should return a list of Shift', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            fromTime: currentDate.format(DATE_TIME_FORMAT),
            toTime: currentDate.format(DATE_TIME_FORMAT),
            workingHrs: 1,
            monthlyPaidLeaves: 1,
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

      it('should delete a Shift', () => {
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
