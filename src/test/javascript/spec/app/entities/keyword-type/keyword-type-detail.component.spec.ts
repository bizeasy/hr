import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { KeywordTypeDetailComponent } from 'app/entities/keyword-type/keyword-type-detail.component';
import { KeywordType } from 'app/shared/model/keyword-type.model';

describe('Component Tests', () => {
  describe('KeywordType Management Detail Component', () => {
    let comp: KeywordTypeDetailComponent;
    let fixture: ComponentFixture<KeywordTypeDetailComponent>;
    const route = ({ data: of({ keywordType: new KeywordType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [KeywordTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(KeywordTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KeywordTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load keywordType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.keywordType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
