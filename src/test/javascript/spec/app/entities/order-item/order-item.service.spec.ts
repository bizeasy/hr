import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OrderItemService } from 'app/entities/order-item/order-item.service';
import { IOrderItem, OrderItem } from 'app/shared/model/order-item.model';

describe('Service Tests', () => {
  describe('OrderItem Service', () => {
    let injector: TestBed;
    let service: OrderItemService;
    let httpMock: HttpTestingController;
    let elemDefault: IOrderItem;
    let expectedResult: IOrderItem | IOrderItem[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(OrderItemService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new OrderItem(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, currentDate, currentDate, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            estimatedShipDate: currentDate.format(DATE_TIME_FORMAT),
            estimatedDeliveryDate: currentDate.format(DATE_TIME_FORMAT),
            shipBeforeDate: currentDate.format(DATE_TIME_FORMAT),
            shipAfterDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a OrderItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            estimatedShipDate: currentDate.format(DATE_TIME_FORMAT),
            estimatedDeliveryDate: currentDate.format(DATE_TIME_FORMAT),
            shipBeforeDate: currentDate.format(DATE_TIME_FORMAT),
            shipAfterDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            estimatedShipDate: currentDate,
            estimatedDeliveryDate: currentDate,
            shipBeforeDate: currentDate,
            shipAfterDate: currentDate,
          },
          returnedFromService
        );

        service.create(new OrderItem()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a OrderItem', () => {
        const returnedFromService = Object.assign(
          {
            sequenceNo: 1,
            quantity: 1,
            cancelQuantity: 1,
            selectedAmount: 1,
            unitPrice: 1,
            unitListPrice: 1,
            cgst: 1,
            igst: 1,
            sgst: 1,
            cgstPercentage: 1,
            igstPercentage: 1,
            sgstPercentage: 1,
            totalPrice: 1,
            isModifiedPrice: true,
            estimatedShipDate: currentDate.format(DATE_TIME_FORMAT),
            estimatedDeliveryDate: currentDate.format(DATE_TIME_FORMAT),
            shipBeforeDate: currentDate.format(DATE_TIME_FORMAT),
            shipAfterDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            estimatedShipDate: currentDate,
            estimatedDeliveryDate: currentDate,
            shipBeforeDate: currentDate,
            shipAfterDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of OrderItem', () => {
        const returnedFromService = Object.assign(
          {
            sequenceNo: 1,
            quantity: 1,
            cancelQuantity: 1,
            selectedAmount: 1,
            unitPrice: 1,
            unitListPrice: 1,
            cgst: 1,
            igst: 1,
            sgst: 1,
            cgstPercentage: 1,
            igstPercentage: 1,
            sgstPercentage: 1,
            totalPrice: 1,
            isModifiedPrice: true,
            estimatedShipDate: currentDate.format(DATE_TIME_FORMAT),
            estimatedDeliveryDate: currentDate.format(DATE_TIME_FORMAT),
            shipBeforeDate: currentDate.format(DATE_TIME_FORMAT),
            shipAfterDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            estimatedShipDate: currentDate,
            estimatedDeliveryDate: currentDate,
            shipBeforeDate: currentDate,
            shipAfterDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a OrderItem', () => {
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
