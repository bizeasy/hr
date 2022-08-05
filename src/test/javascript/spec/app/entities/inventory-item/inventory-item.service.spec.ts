import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { InventoryItemService } from 'app/entities/inventory-item/inventory-item.service';
import { IInventoryItem, InventoryItem } from 'app/shared/model/inventory-item.model';

describe('Service Tests', () => {
  describe('InventoryItem Service', () => {
    let injector: TestBed;
    let service: InventoryItemService;
    let httpMock: HttpTestingController;
    let elemDefault: IInventoryItem;
    let expectedResult: IInventoryItem | IInventoryItem[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InventoryItemService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new InventoryItem(
        0,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            receivedDate: currentDate.format(DATE_TIME_FORMAT),
            manufacturedDate: currentDate.format(DATE_TIME_FORMAT),
            expiryDate: currentDate.format(DATE_TIME_FORMAT),
            retestDate: currentDate.format(DATE_TIME_FORMAT),
            activationValidTrue: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a InventoryItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            receivedDate: currentDate.format(DATE_TIME_FORMAT),
            manufacturedDate: currentDate.format(DATE_TIME_FORMAT),
            expiryDate: currentDate.format(DATE_TIME_FORMAT),
            retestDate: currentDate.format(DATE_TIME_FORMAT),
            activationValidTrue: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            receivedDate: currentDate,
            manufacturedDate: currentDate,
            expiryDate: currentDate,
            retestDate: currentDate,
            activationValidTrue: currentDate,
          },
          returnedFromService
        );

        service.create(new InventoryItem()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a InventoryItem', () => {
        const returnedFromService = Object.assign(
          {
            receivedDate: currentDate.format(DATE_TIME_FORMAT),
            manufacturedDate: currentDate.format(DATE_TIME_FORMAT),
            expiryDate: currentDate.format(DATE_TIME_FORMAT),
            retestDate: currentDate.format(DATE_TIME_FORMAT),
            containerId: 'BBBBBB',
            batchNo: 'BBBBBB',
            mfgBatchNo: 'BBBBBB',
            lotNoStr: 'BBBBBB',
            binNumber: 'BBBBBB',
            comments: 'BBBBBB',
            quantityOnHandTotal: 1,
            availableToPromiseTotal: 1,
            accountingQuantityTotal: 1,
            oldQuantityOnHand: 1,
            oldAvailableToPromise: 1,
            serialNumber: 'BBBBBB',
            softIdentifier: 'BBBBBB',
            activationNumber: 'BBBBBB',
            activationValidTrue: currentDate.format(DATE_TIME_FORMAT),
            unitCost: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            receivedDate: currentDate,
            manufacturedDate: currentDate,
            expiryDate: currentDate,
            retestDate: currentDate,
            activationValidTrue: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of InventoryItem', () => {
        const returnedFromService = Object.assign(
          {
            receivedDate: currentDate.format(DATE_TIME_FORMAT),
            manufacturedDate: currentDate.format(DATE_TIME_FORMAT),
            expiryDate: currentDate.format(DATE_TIME_FORMAT),
            retestDate: currentDate.format(DATE_TIME_FORMAT),
            containerId: 'BBBBBB',
            batchNo: 'BBBBBB',
            mfgBatchNo: 'BBBBBB',
            lotNoStr: 'BBBBBB',
            binNumber: 'BBBBBB',
            comments: 'BBBBBB',
            quantityOnHandTotal: 1,
            availableToPromiseTotal: 1,
            accountingQuantityTotal: 1,
            oldQuantityOnHand: 1,
            oldAvailableToPromise: 1,
            serialNumber: 'BBBBBB',
            softIdentifier: 'BBBBBB',
            activationNumber: 'BBBBBB',
            activationValidTrue: currentDate.format(DATE_TIME_FORMAT),
            unitCost: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            receivedDate: currentDate,
            manufacturedDate: currentDate,
            expiryDate: currentDate,
            retestDate: currentDate,
            activationValidTrue: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a InventoryItem', () => {
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
