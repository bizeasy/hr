import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ItemIssuanceService } from 'app/entities/item-issuance/item-issuance.service';
import { IItemIssuance, ItemIssuance } from 'app/shared/model/item-issuance.model';

describe('Service Tests', () => {
  describe('ItemIssuance Service', () => {
    let injector: TestBed;
    let service: ItemIssuanceService;
    let httpMock: HttpTestingController;
    let elemDefault: IItemIssuance;
    let expectedResult: IItemIssuance | IItemIssuance[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ItemIssuanceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ItemIssuance(0, 'AAAAAAA', currentDate, 'AAAAAAA', 0, 0, currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            issuedDate: currentDate.format(DATE_TIME_FORMAT),
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            thruDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ItemIssuance', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            issuedDate: currentDate.format(DATE_TIME_FORMAT),
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            thruDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            issuedDate: currentDate,
            fromDate: currentDate,
            thruDate: currentDate,
          },
          returnedFromService
        );

        service.create(new ItemIssuance()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ItemIssuance', () => {
        const returnedFromService = Object.assign(
          {
            message: 'BBBBBB',
            issuedDate: currentDate.format(DATE_TIME_FORMAT),
            issuedBy: 'BBBBBB',
            quantity: 1,
            cancelQuantity: 1,
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            thruDate: currentDate.format(DATE_TIME_FORMAT),
            equipmentId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            issuedDate: currentDate,
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

      it('should return a list of ItemIssuance', () => {
        const returnedFromService = Object.assign(
          {
            message: 'BBBBBB',
            issuedDate: currentDate.format(DATE_TIME_FORMAT),
            issuedBy: 'BBBBBB',
            quantity: 1,
            cancelQuantity: 1,
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            thruDate: currentDate.format(DATE_TIME_FORMAT),
            equipmentId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            issuedDate: currentDate,
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

      it('should delete a ItemIssuance', () => {
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
