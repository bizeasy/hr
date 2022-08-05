import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ShiftHolidaysService } from 'app/entities/shift-holidays/shift-holidays.service';
import { IShiftHolidays, ShiftHolidays } from 'app/shared/model/shift-holidays.model';
import { DayOfheWeek } from 'app/shared/model/enumerations/day-ofhe-week.model';

describe('Service Tests', () => {
  describe('ShiftHolidays Service', () => {
    let injector: TestBed;
    let service: ShiftHolidaysService;
    let httpMock: HttpTestingController;
    let elemDefault: IShiftHolidays;
    let expectedResult: IShiftHolidays | IShiftHolidays[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ShiftHolidaysService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ShiftHolidays(0, DayOfheWeek.SATURDAY, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ShiftHolidays', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ShiftHolidays()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ShiftHolidays', () => {
        const returnedFromService = Object.assign(
          {
            dayOfheWeek: 'BBBBBB',
            monthlyPaidLeaves: 1,
            yearlyPaidLeaves: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ShiftHolidays', () => {
        const returnedFromService = Object.assign(
          {
            dayOfheWeek: 'BBBBBB',
            monthlyPaidLeaves: 1,
            yearlyPaidLeaves: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ShiftHolidays', () => {
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
