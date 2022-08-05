import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { InventoryTransferService } from 'app/entities/inventory-transfer/inventory-transfer.service';
import { IInventoryTransfer, InventoryTransfer } from 'app/shared/model/inventory-transfer.model';

describe('Service Tests', () => {
  describe('InventoryTransfer Service', () => {
    let injector: TestBed;
    let service: InventoryTransferService;
    let httpMock: HttpTestingController;
    let elemDefault: IInventoryTransfer;
    let expectedResult: IInventoryTransfer | IInventoryTransfer[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InventoryTransferService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new InventoryTransfer(0, currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            sentDate: currentDate.format(DATE_TIME_FORMAT),
            receivedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a InventoryTransfer', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            sentDate: currentDate.format(DATE_TIME_FORMAT),
            receivedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sentDate: currentDate,
            receivedDate: currentDate,
          },
          returnedFromService
        );

        service.create(new InventoryTransfer()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a InventoryTransfer', () => {
        const returnedFromService = Object.assign(
          {
            sentDate: currentDate.format(DATE_TIME_FORMAT),
            receivedDate: currentDate.format(DATE_TIME_FORMAT),
            comments: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sentDate: currentDate,
            receivedDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of InventoryTransfer', () => {
        const returnedFromService = Object.assign(
          {
            sentDate: currentDate.format(DATE_TIME_FORMAT),
            receivedDate: currentDate.format(DATE_TIME_FORMAT),
            comments: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sentDate: currentDate,
            receivedDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a InventoryTransfer', () => {
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
