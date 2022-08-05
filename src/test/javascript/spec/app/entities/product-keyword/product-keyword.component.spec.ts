import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { ProductKeywordComponent } from 'app/entities/product-keyword/product-keyword.component';
import { ProductKeywordService } from 'app/entities/product-keyword/product-keyword.service';
import { ProductKeyword } from 'app/shared/model/product-keyword.model';

describe('Component Tests', () => {
  describe('ProductKeyword Management Component', () => {
    let comp: ProductKeywordComponent;
    let fixture: ComponentFixture<ProductKeywordComponent>;
    let service: ProductKeywordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductKeywordComponent],
      })
        .overrideTemplate(ProductKeywordComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductKeywordComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductKeywordService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductKeyword(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productKeywords && comp.productKeywords[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
