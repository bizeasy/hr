import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EmplPositionService } from 'app/entities/empl-position/empl-position.service';
import { IEmplPosition, EmplPosition } from 'app/shared/model/empl-position.model';

describe('Service Tests', () => {
  describe('EmplPosition Service', () => {
    let injector: TestBed;
    let service: EmplPositionService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmplPosition;
    let expectedResult: IEmplPosition | IEmplPosition[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EmplPositionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new EmplPosition(0, 0, 0, currentDate, currentDate, false, false, false, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            estimatedFromDate: currentDate.format(DATE_FORMAT),
            estimatedThruDate: currentDate.format(DATE_FORMAT),
            actualFromDate: currentDate.format(DATE_FORMAT),
            actualThruDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EmplPosition', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            estimatedFromDate: currentDate.format(DATE_FORMAT),
            estimatedThruDate: currentDate.format(DATE_FORMAT),
            actualFromDate: currentDate.format(DATE_FORMAT),
            actualThruDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            estimatedFromDate: currentDate,
            estimatedThruDate: currentDate,
            actualFromDate: currentDate,
            actualThruDate: currentDate,
          },
          returnedFromService
        );

        service.create(new EmplPosition()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EmplPosition', () => {
        const returnedFromService = Object.assign(
          {
            maxBudget: 1,
            minBudget: 1,
            estimatedFromDate: currentDate.format(DATE_FORMAT),
            estimatedThruDate: currentDate.format(DATE_FORMAT),
            paidJob: true,
            isFulltime: true,
            isTemporary: true,
            actualFromDate: currentDate.format(DATE_FORMAT),
            actualThruDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            estimatedFromDate: currentDate,
            estimatedThruDate: currentDate,
            actualFromDate: currentDate,
            actualThruDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EmplPosition', () => {
        const returnedFromService = Object.assign(
          {
            maxBudget: 1,
            minBudget: 1,
            estimatedFromDate: currentDate.format(DATE_FORMAT),
            estimatedThruDate: currentDate.format(DATE_FORMAT),
            paidJob: true,
            isFulltime: true,
            isTemporary: true,
            actualFromDate: currentDate.format(DATE_FORMAT),
            actualThruDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            estimatedFromDate: currentDate,
            estimatedThruDate: currentDate,
            actualFromDate: currentDate,
            actualThruDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EmplPosition', () => {
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
