import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { InventoryItemDelegateService } from 'app/entities/inventory-item-delegate/inventory-item-delegate.service';
import { IInventoryItemDelegate, InventoryItemDelegate } from 'app/shared/model/inventory-item-delegate.model';

describe('Service Tests', () => {
  describe('InventoryItemDelegate Service', () => {
    let injector: TestBed;
    let service: InventoryItemDelegateService;
    let httpMock: HttpTestingController;
    let elemDefault: IInventoryItemDelegate;
    let expectedResult: IInventoryItemDelegate | IInventoryItemDelegate[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InventoryItemDelegateService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new InventoryItemDelegate(0, 0, currentDate, 0, 0, 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            effectiveDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a InventoryItemDelegate', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            effectiveDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            effectiveDate: currentDate,
          },
          returnedFromService
        );

        service.create(new InventoryItemDelegate()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a InventoryItemDelegate', () => {
        const returnedFromService = Object.assign(
          {
            sequenceNo: 1,
            effectiveDate: currentDate.format(DATE_TIME_FORMAT),
            quantityOnHandDiff: 1,
            availableToPromiseDiff: 1,
            accountingQuantityDiff: 1,
            unitCost: 1,
            remarks: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            effectiveDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of InventoryItemDelegate', () => {
        const returnedFromService = Object.assign(
          {
            sequenceNo: 1,
            effectiveDate: currentDate.format(DATE_TIME_FORMAT),
            quantityOnHandDiff: 1,
            availableToPromiseDiff: 1,
            accountingQuantityDiff: 1,
            unitCost: 1,
            remarks: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            effectiveDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a InventoryItemDelegate', () => {
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
